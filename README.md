# Brian's Scala Scripts
Collection of utilities built with Scala Native that I use in various ways.

## find-projects
Lists all directories that contain a standard build tool file
for python (`pyproject.toml`) or scala (`build.sbt`/`build.mill`/`project.scala`) at the root.
Used as an external command by Wezterm to create a project picker. See [this part]() of my wezterm
config.

## sc
Automatically launches the build tool for the scala project in the directory by looking at build
file names.
