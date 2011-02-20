[chapter Tutorial]

This is a good example to start with:

[java]
final public class A {

	public static void main(final String[] args) {
		Template<Item> template = new SingleTemplate<Item>(
					"<h1>${content}</h1>", Item.class);
		Item item = template.match(new Html(
					"<h1 class='content'>what <br /> you <b> really </b> want</h1>"));
		System.out.println(item.getContent());
	}

	static class Item {
		String content;

		public String getContent() {
			return content;
		}
	}
}
[/java]

First of all, you need to create a template (in this example, a SingleTemplate). When you create a template, you specify what is the structure of the content you want to extract.

After that, you tells your template to be matched against an Html and you'll receive the object created with the matches.

That's it. It's this simple. 