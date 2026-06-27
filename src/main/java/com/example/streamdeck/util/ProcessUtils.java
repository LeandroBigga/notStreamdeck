package com.example.streamdeck.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

    public static List<String> getRunningProcesses() {

        List<String> processes = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"cmd", "/c", "tasklist /fo csv /nh"});

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.replace("\"", "");
                String[] parts = line.split(",");

                if (parts.length > 0) {
                    String name = parts[0];

                    if (name.endsWith(".exe") && !processes.contains(name)) {
                        processes.add(name);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }


}
