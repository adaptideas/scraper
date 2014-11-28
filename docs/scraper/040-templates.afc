[chapter Templates]

As of this release, we have two types of templates. ::SingleTemplate:: and ::SplitTemplate::.

[section SingleTemplate]

This is the most basic scraper template. It's the one that implement our matching rules. Take a look at Matching.

[section SplitTemplate]

This is a template designed to extract data from heterogeneous Html strings. It basically works like this:

[code]
Template Code

<split>

Template Code

<split>

Template Code
[/code]

You can add as much ::<split>::s as you want. The ::<split>:: tag marks the start of where ::SplitTemplate:: 
will consider the template code as a new template. Basically, this is the same as creating 3 ::SingleTemplate::s
and applying them one after another until you can extract data.