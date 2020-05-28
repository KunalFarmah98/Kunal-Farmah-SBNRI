# Kunal-Farmah-SBNRI
SBNRI  Assignment

## Uses Github API: https://api.github.com/orgs/octokit/repos?page=1&per_page=10

### Uses RecyclerView to show the items.

### Uses MVVM pattern for the architecture (View, Viewmodel, Web and Room Repository). 
Screen Orientation not handeled explicitly, handled internally by lifecycle components

### Added layouts for landscape mode

### Added pagination to allow the user to scroll down to load more results.

### Added animations on orientation changes.

### Stored the data in Room.

### Added open_issues_count, license, permissions, name and description field for each cell in the recycler view. Took care of "null" values in API and displayed them appropriately.

