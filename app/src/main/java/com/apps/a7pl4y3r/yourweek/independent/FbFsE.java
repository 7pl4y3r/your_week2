package com.apps.a7pl4y3r.yourweek.independent;

import com.apps.a7pl4y3r.yourweek.databases.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FbFsE {

    public static void see(DocumentSnapshot it) {

        Map map = it.getData();
        List list = (ArrayList<Task>) map.get("Day");

    }

}
