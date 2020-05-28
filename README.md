# Kunal-Farmah-SBNRI
SBNRI  Assignment

## Uses Github API: https://api.github.com/orgs/octokit/repos?page=1&per_page=10

### Uses RecyclerView to show the items.

### Uses MVVM pattern for the architecture (View, Viewmodel, Web and Room Repository). 
### Screen Orientation not handled explicitly, handled internally by lifecycle components.  I have not locked the orientation by default.
### (so, any crashes while rotating would be due to internal incomaptibility, not due to error in code).

### Added layouts for landscape mode.

### Added pagination to allow the user to scroll down to load more results.

### Added animations on orientation changes.

### Stored the data in Room.

### Added open_issues_count, license, permissions, name and description field for each cell in the recycler view. Took care of "null" values in API and displayed them appropriately.

<p> <img hspace="10" src="https://github.com/KunalFarmah98/Kunal-Farmah-SBNRI/blob/master/app/src/main/res/raw/port.jpg" width =200 
  height = 350/>
 <img hspace="10" src="https://github.com/KunalFarmah98/Kunal-Farmah-SBNRI/blob/master/app/src/main/res/raw/land.jpg" width =350 
  height = 200/></p>

