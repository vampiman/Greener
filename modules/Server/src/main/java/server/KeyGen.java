package server;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyGen {
    public static final Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes());

    static final Key KEY_VALIDATE = Keys.hmacShaKeyFor(
            "THISISEVENHARDERTHISISTHEKEYTHATONLYSERVERUSESITSSUCHAGREATSECRETITWILLBLOWYOURMIND"
                    .getBytes());
}
