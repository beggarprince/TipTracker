# TipTracker
Simple app that user can track trips with. Aimed at delivery drivers
Uses
  SingleActivity + Fragments
  MVVM
  Room Database

TODO
  Update UI - MAKE IT PRETTY GOES WITHOUT SAYING, it's ugly atm
      Stats
          Have a background for each icon to make them stand out better in dark mode and resemble a card
          Line Chart might be useful to cover up the top 40-60% of the screen using 
      AddTrip
          Nothing to add, just update the appearance to make it more presentable
      Home
          Maybe cover the top 30-40% of the screen with either a line chart, or some stats such as how many items in recycler view, days worked, etc
          Have a filter button to be able to sort the items in the recyclerview not by date but things such as money earned or hours worked.
      Trip XMLCard
          Get rid of the year, add id to differentiate trips on the same day (1 default, incrementing. not the Room db id)
          Delete button
                  Replace with a trash can icon
                  Swipe to delete + "Are you sure" fragment
      MainActivity
          Replace the "AddTrip" icon as the "+" looks awful
          Tips icon looks bad in night mode
  NIGHT MODE
        Choose better colors.