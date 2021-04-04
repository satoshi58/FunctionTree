package tk.sistemainformatico.functontree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Map<String, String> m_project_data;
    public static List<Map<String, String>> m_project_dataList;
    public static ListView m_project_listView;
    public static ProjectListViewAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activate Buttons
        Button artistsButton = (Button) findViewById(R.id.button_artists);
        Button albumsButton = (Button) findViewById(R.id.button_albums);
        Button songsButton = (Button) findViewById(R.id.button_songs);

        m_project_dataList = new ArrayList<Map<String, String>>();

        File directory = getDir("FunctionTreeProjects", Context.MODE_PRIVATE);
        String[] project_files = directory.list(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String fileName) {
                return true;
            }
        });

        for (String project_file : project_files) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(project_file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while(line != null){
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                String responce = stringBuilder.toString();
                JSONObject jsonObject  = new JSONObject(responce);
                String project_name = jsonObject.get("project_name").toString();
                String project_description = jsonObject.get("project_description").toString();

                m_project_data = new HashMap<String, String>();
                m_project_data.put("project_name", project_name);
                m_project_data.put("project_description", project_description);

                m_project_dataList.add(m_project_data);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        m_project_data = new HashMap<String, String>();
        m_project_data.put("project_name", "+");
        m_project_data.put("project_description", "");
        m_project_dataList.add(m_project_data);

        m_adapter = new ProjectListViewAdapter(
                this,
                m_project_dataList,
                R.layout.row_project,
                new String[] { "project_name", "project_description" },
                new int[] { R.id.project_name, R.id.project_description });

        // アダプターにDATAをSETする
        m_project_listView = (ListView) findViewById(R.id.project_listview);
        m_project_listView.setAdapter(m_adapter);
        m_project_listView.setTextFilterEnabled(false);

        m_project_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListView listView = (ListView) parent;
                String item = (String) listView.getItemAtPosition(position);
            }
        });
    }
}