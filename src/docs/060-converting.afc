[chapter Converting data]

At this point, scraper simply won't try to convert the data found. Most of the time we just wanted to extract strings from Html, but you can implement your own converters (and maybe send them to us :) )

You just need to implement the interface ::br.com.adaptideas.scraper.converter.Converter:: and add it to the ::br.com.adaptideas.scraper.converter.DataConverter:: passed to your template.

[java]
new SingleTemplate("template code", Your.class, 
		new DataConverter(Arrays.asList(new YourAmazingConverter())));
[/java]