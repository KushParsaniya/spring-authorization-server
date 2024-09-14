package dev.kush.authorizationserver.enums;

public enum AvailableClientAuthorizationMethod {
    client_secret_basic,
    client_secret_post,
    client_secret_jwt,
    private_key_jwt,
    none,
    tls_client_auth,
    self_signed_tls_client_auth;
}
