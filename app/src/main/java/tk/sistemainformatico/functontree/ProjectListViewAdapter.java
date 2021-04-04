package tk.sistemainformatico.functontree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectListViewAdapter extends SimpleAdapter {

    private LayoutInflater m_inflater;
    private List<? extends Map<String, ?>> m_listData;

    // 各行が保持するデータ保持クラス
    public class ViewHolder {
        TextView project_name;
    }

    public ProjectListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.m_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.m_listData = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // ビューを受け取る
        View view = convertView;

        if (view == null) {
            // 画面起動時にViewHolderを作成する
            view = m_inflater.inflate(R.layout.row_project, parent, false);

            holder = new ViewHolder();
            holder.project_name = (TextView) view.findViewById(R.id.project_name);

            view.setTag(holder);
        } else {
            // 行選択時などは既に作成されているものを取得する
            holder = (ViewHolder) view.getTag();
        }

        // holderにデータをセットする
        String project_name = ((HashMap<?, ?>) m_listData.get(position)).get("project_name").toString();
        holder.project_name.setText(project_name);

        // セル上にあるボタンの処理
        Button btn = (Button) view.findViewById(R.id.edit_button);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //
            }
        });

        return view;
    }
}
