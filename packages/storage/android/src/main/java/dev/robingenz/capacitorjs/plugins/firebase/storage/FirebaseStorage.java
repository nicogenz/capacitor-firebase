package dev.robingenz.capacitorjs.plugins.firebase.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorage {

    private com.google.firebase.storage.FirebaseStorage storageInstance;

    public FirebaseStorage() {
        storageInstance = com.google.firebase.storage.FirebaseStorage.getInstance();
    }

    public StorageReference getReference(@Nullable String location) {
        return location == null ? storageInstance.getReference() : storageInstance.getReference(location);
    }

    public Task<Void> delete(@NonNull String location) {
        return getReference(location).delete();
    }
}
