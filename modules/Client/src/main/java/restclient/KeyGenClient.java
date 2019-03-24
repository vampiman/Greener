package restclient;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyGenClient {
    public static final Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes());
}
