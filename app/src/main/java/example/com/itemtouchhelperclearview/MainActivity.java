package example.com.itemtouchhelperclearview;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 10; i++) {
            items.add("Item " + (i + 1));
        }

        setContentView(R.layout.activity_main);

        final RecyclerView list = (RecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.text.setText(items.get(position));
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
        // If you comment out this line, it will work correctly.
        list.addItemDecoration(new PaddingDecoration(getResources().getDimensionPixelSize(R.dimen.divider_size)));
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int state) {
                int position = viewHolder.getAdapterPosition();
                items.remove(position);
                list.getAdapter().notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View overlay = viewHolder.itemView.findViewById(R.id.overlay);
                overlay.setTranslationX(dX);
                overlay.setTranslationY(dY);
                super.onChildDraw(c, recyclerView, viewHolder, 0, 0, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                // This won't be called for all items.
                Log.d("ItemTouchHelper", "clearView");
            }
        });
        helper.attachToRecyclerView(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
