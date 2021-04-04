package tk.sistemainformatico.functontree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
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

        File file = new File(getApplicationContext().getFilesDir(), "");
        File directory = new File(file.getParent());
        String[] fileNames = directory.list(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String fileName) {
                return true;
            }
        });

        int MAXDATA = 10;
        for (int i = 0; i < MAXDATA; i++) {
            m_project_data = new HashMap<String, String>();
            m_project_data.put("project_name", "Project" + i);
            m_project_dataList.add(m_project_data);
        }

        m_adapter = new ProjectListViewAdapter(
                this,
                m_project_dataList,
                R.layout.row_project,
                new String[] { "text1", "text2" },
                new int[] { android.R.id.text1,
                        android.R.id.text2 });

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