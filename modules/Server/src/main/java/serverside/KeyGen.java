package serverside;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

class KeyGen {
    static final Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes());

    static final Key KEY_VALIDATE = Keys.hmacShaKeyFor(
            "THISISEVENHARDERTHISISTHEKEYTHATONLYSERVERUSESITSSUCHAGREATSECRETITWILLBLOWYOURMIND"
                    .getBytes());
}
