package com.mapeando.territory.util;

import com.google.firebase.auth.FirebaseToken;

public class FirebaseTokenWrapper implements FirebaseTokenProvider {
    private final FirebaseToken firebaseToken;

    public FirebaseTokenWrapper(FirebaseToken firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    @Override
    public String getSomeValue() {
        // Delegate to the actual FirebaseToken method or return a mock value
        return firebaseToken.getUid();
    }

    // Implement other methods from the interface by delegating to the real FirebaseToken
}


