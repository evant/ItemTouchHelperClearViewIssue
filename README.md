# ItemTouchHelperClearViewIssue
Issue with using ItemTouchHelper and an ItemDecoration

Swipe away items from the top to the bottom, you'll see that they aren't acutally removed from the view hierarchy, (the trash can will still be there). If you comment out the line which adds an ItemDecoration, it works correctly.
