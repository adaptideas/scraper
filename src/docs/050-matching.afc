[chapter Matching]

Scraper uses some quite different ideas on how to extract data from html. This is probably one of the most important pieces of documentation we have here.

[section Strategy]

Scraper will simply ignore the existence of tags that are not mentioned on the template. For example:

If your template is:

[code]
<h1>${content}</h1>
[/code]

And your html is:

[code]
<body>
	<p> Something, something something </p>
	<h1> 
		your <b> text </b>
	</h1>
</body>
[/code]

Scraper will look at your html as if it was:

[code]
<h1>your text</h1>
[/code]

As you can see, the tags ::body::, ::p::. ::/p::, ::b::, ::/b:: and ::/body:: will be completely ignored because they are not mentioned on the template.

But ignoring tag does not mean ignoring text. Anytime a tag is removed, it's content (text) is appended to the previous tag. 

Ah, scraper has no notion of nesting or well-formed html. It simply recognizes anything inside :: < :: and :: > :: as a tag. Why did we do that? Because the web is a really heterogeneous environment. After analyzing more than 20,000 pages, we found lots of crappy coded pages, including mistyped tags, invalid tags, crazy tags on strange namespaces (I'm looking at you, MicroSoft!) and a whole lot more of things that shouldn't exist. The only way we found of consistently parsing that was being able to execute on any kind of awful environment. This also brought a benefit. We can extract information from anything that is marked using :: < :: and :: > ::. This includes any Html, Xml or your custom format!

[section Tag name]

After tag elimination (described on the previous section), we start matching the template against the Html. The first thing we take into consideration is the tag name. Same tag name and order determine a match.

[code]
<h1>${content}</h1>
[/code]

Will match

[code]
<h1>${content}</h1>
[/code]

But won't match

[code]
</h1>${content}<h1>
[/code]

[section Tag Attributes]

Tag attributes can be used to determine structure (really useful when running against a tableful page). You can use single quotes ::':: or double quotes ::":: because scraper really doesn't care.

[code]
<h1 class="foo">${content}</h1>
[/code]

Will match

[code]
<h1 class='foo'>your content</h1>
[/code]

And also match

[code]
<h1 class="something foo bar">your content</h1>
[/code]

but won't match

[code]
<h1>your content</h1>
[/code]

or

[code]
<h1 class="bar">your content</h1>
[/code]

[section Tag content]

While we used Scraper (prior to the first public release), sometimes we had some problems determining the structure of the template using only tags. So, we started to take into account the content of tags on the template.

For example:

[code]
<h1>Content ${content}</h1>
[/code]

Will match:

[code]
<h1>Content something</h1>
[/code]

And will capture "something"

But won't match:

[code]
<h1>something</h1>
[/code]

This is quite useful on tableful pages that it's hard to determine a unique structure.

[section Capture Groups]

At this point you have probably notice that we always have something like ::${content}:: on our templates. This is what we call **Capture Group**. The word inside ::${}:: must be the name of a field you have on the class you passed to the template. You may have as many Capture Groups as you want on a template.

[section Elipsis]

Another problem we usually had when using Scraper was that it is somewhat easier to create a start point than the ending point. To solve this, we created a matcher that simply tells scraper to ignore anything from that point on.

Example:

[code]
<h1>Duration: ${content} h...
[/code]

will match:

[code]
<h1>Duration: 8 h/month</h1>
[/code]
(will retrive 8)

and 

[code]
<h1>Duration: 16 hours per week and anything you want here.
[/code]
(will retrieve 16)

But won't match:

[code]
<h1>Duration: 2 days</h1>
[/code]