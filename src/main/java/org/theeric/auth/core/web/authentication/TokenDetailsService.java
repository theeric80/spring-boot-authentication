package org.theeric.auth.core.web.authentication;

public interface TokenDetailsService {

    TokenDetails loadTokenByCredential(String credential);

}
