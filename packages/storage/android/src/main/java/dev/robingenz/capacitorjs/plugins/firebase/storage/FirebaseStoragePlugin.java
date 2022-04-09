package dev.robingenz.capacitorjs.plugins.firebase.storage;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.IOException;

@CapacitorPlugin(name = "FirebaseStorage")
public class FirebaseStoragePlugin extends Plugin {

    private static final String ERROR_FILE_LOCATION_MISSING = "file location must be provided.";
    private static final String ERROR_FILE_NAME_MISSING = "file name must be provided.";
    private static final String ERROR_FILE_EXTENSION_MISSING = "file extension must be provided.";
    private FirebaseStorage implementation;

    @Override
    public void load() {
        implementation = new FirebaseStorage();
    }

    @PluginMethod
    public void delete(PluginCall call) {
        String location = call.getString("location");
        if (location == null || location.trim().isEmpty()) {
            call.reject(ERROR_FILE_LOCATION_MISSING);
            return;
        }
        implementation.delete(location)
                .addOnSuccessListener(aVoid -> call.resolve())
                .addOnFailureListener(exception -> call.reject(exception.getMessage(), exception));
    }

    @PluginMethod
    public void getBytes(PluginCall call) {
        String location = call.getString("location");
        if (location == null || location.trim().isEmpty()) {
            call.reject(ERROR_FILE_LOCATION_MISSING);
            return;
        }
        implementation.getBytes(location)
                .addOnSuccessListener(bytes -> {
                    JSObject jsObject = new JSObject();
                    jsObject.put("bytes", bytes);
                    call.resolve(jsObject);
                })
                .addOnFailureListener(exception -> call.reject(exception.getMessage(), exception));
    }

    @PluginMethod
    public void getFile(PluginCall call) {
        String location = call.getString("location");
        String name = call.getString("name");
        String extension = call.getString("extension");
        if (location == null || location.trim().isEmpty()) {
            call.reject(ERROR_FILE_LOCATION_MISSING);
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            call.reject(ERROR_FILE_NAME_MISSING);
            return;
        }
        if (extension == null || extension.trim().isEmpty()) {
            call.reject(ERROR_FILE_EXTENSION_MISSING);
            return;
        }
        try {
            implementation.getFile(location, name, extension)
                    .addOnSuccessListener(snapshot -> call.resolve())
                    .addOnFailureListener(exception -> call.reject(exception.getMessage(), exception));
        } catch (IOException exception) {
            call.reject(exception.getMessage(), exception);
        }
    }
}
