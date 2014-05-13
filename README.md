myWebsite
=========

<<<<<<< HEAD
My Courses
=======
You can get a Git project using two main approaches. The first takes an existing project or directory and imports it into Git. The second clones an existing Git repository from another server.

## Initializing a Repository in an Existing Directory

If you’re starting to track an existing project in Git, you need to go to the project’s directory and type

`$ git init`

This creates a new subdirectory named **`.git` that contains all of your necessary repository files — a Git repository skeleton. At this point, nothing in your project is tracked yet. (See Chapter 9 for more information about exactly what files are contained in the **`.git` directory you just created.)

If you want to start version-controlling existing files (as opposed to an empty directory), you should probably begin tracking those files and do an initial commit. You can accomplish that with a few git add commands that specify the files you want to track, followed by a commit:

`$ git add *.js`

`$ git add README`

`$ git commit -m 'initial project version'`

At this point, you have a Git repository with tracked files and an initial commit.

## Cloning an Existing Repository

If you want to get a copy of an existing Git repository — for example, a project you’d like to contribute to — the command you need is `git clone`. If you’re familiar with other VCS systems such as Subversion, you’ll notice that the command is `clone` and not `checkout`. This is an important distinction — Git receives a copy of nearly all data that the server has. Every version of every file for the history of the project is pulled down when you run `git clone`. In fact, if your server disk gets corrupted, you can use any of the clones on any client to set the server back to the state it was in when it was.

You clone a repository with `git clone [url]`. For example, if you want to clone the Ruby Git library called Grit, you can do so like this:

`$ git clone git://github.com/HardiBhattatWork/myWebsite.git`

That creates a directory named `grit`, initializes a .`git` directory inside it, pulls down all the data for that repository, and checks out a working copy of the latest version. If you go into the new grit directory, you’ll see the project files in there, ready to be worked on or used. If you want to clone the repository into a directory named something other than `grit`, you can specify that as the next command-line option:

`$ git clone git://github.com/HardiBhattatWork/myWebsite.git myDirectory` 

That command does the same thing as the previous one, but the target directory is called `mygrit`

## Adding Git to Windows 7 Path

Note: You must have msysgit installed on your machine. Also, the path to my git install is "C:\Program Files (x86)\Git" yours might be different. Please check were yours is before continuing.

### Open Windows Environment Variables/Path Window

·         Right-Click on **My Computer**

·         Click **Advanced System Settings** link from the left side column

·         Click **Environment Variables** in the bottom of the window

·         Then under **System Variables** look for the **path** variable and click **edit**

·         Add the pwd to git's bin and cmd at the end of the string like this:

`;C:\Program Files (x86)\Git\bin;C:\Program Files (x86)\Git\cmd`

Now test it out in PowerShell; type git and see if it recognizes the command.


>>>>>>> ed797056ed62cb40043a0932ba0c00cc83075e6e
