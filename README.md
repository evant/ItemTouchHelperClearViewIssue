# ItemTouchHelperClearViewIssue
Issue with using ItemTouchHelper and an ItemDecoration

Swipe away items from the top to the bottom, you'll see that they aren't acutally removed from the view hierarchy, (the trash can will still be there). If you comment out the line which adds an ItemDecoration, it works correctly.

## Solution

As suggested in the [issue](https://code.google.com/p/android/issues/detail?id=175798) `invalidateItemDecorations()` needs to be called after the item is removed, because the ItemDecoration is based on the item's positions in the list.

```java
@Override
public void onSwiped(RecyclerView.ViewHolder viewHolder, int state) {
    ...
    viewHolder.itemView.post(new Runnable() {
        @Override
        public void run() {
            recyclerView.invalidateItemDecorations();
        }
    });
}
```
