package com.cars.ui;

import android.widget.EditText;

public class ValidationHelper {
    public static boolean required(EditText ...texts) {
        for (EditText item :
                texts) {
            if(item.length() == 0) return false;
        }
        return true;
    }
}
