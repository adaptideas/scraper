[chapter Scraper]

Given the following html snippet:

[code]
<h1 class="content">
	Your really <b> valuable </b> information
	<p> 
		That is inside a <i>paragraph</i>
	</p>
</h1>
[/code]

And you want to extract all the text, but no tags. You can use RegEx, black magic with XPath or Tree Traversal,
but these are quite hard to maintain. Write once, never understand again.

Using scraper, you can simply tell the structure and it will do the rest:

[code]
<h1>
${content}
</h1>
[/code]

This ::${content}:: tells scraper to fill the attribute content of your data holding object with anything it 
can find inside the ::h1:: **but** the tags. So, content will hold "Your really valuable information That 
is inside a paragraph".

Way easier, isn't it?
	
		