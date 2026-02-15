package org.example.wordlefx.enums;

import javafx.scene.input.KeyCode;

public enum AllowedKey {
    Q(KeyCode.Q),
    W(KeyCode.W),
    E(KeyCode.E),
    R(KeyCode.R),
    T(KeyCode.T),
    Y(KeyCode.Y),
    U(KeyCode.U),
    I(KeyCode.I),
    O(KeyCode.O),
    P(KeyCode.P),
    A(KeyCode.A),
    S(KeyCode.S),
    D(KeyCode.D),
    F(KeyCode.F),
    G(KeyCode.G),
    H(KeyCode.H),
    J(KeyCode.J),
    K(KeyCode.K),
    L(KeyCode.L),
    Z(KeyCode.Z),
    X(KeyCode.X),
    C(KeyCode.C),
    V(KeyCode.V),
    B(KeyCode.B),
    N(KeyCode.N),
    M(KeyCode.M);

    private KeyCode keyCode;

    public static KeyCode findKeyCode(KeyCode keyCode) {
        for(AllowedKey kc : AllowedKey.values()) {
            if(keyCode == kc.getKeyCode()) {
                return kc.getKeyCode();
            }
        }
        return null;
    }

    public static boolean isLetter(KeyCode keyCode) {
        for(AllowedKey allowedKey : AllowedKey.values()) {
            if(keyCode == allowedKey.getKeyCode()) {
                return true;
            }
        }
        return false;
    }

    AllowedKey(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
}
