/**
 * Created by isuca in java-indent-check catalogue
 * Example dangling comment
 *
 * @date 05-May-18
 * @time 16:27
 */

// @formatter:off
public class Example {

/*
  * Example block comment
   */

						public Example() {

												String exampleString = "" +
						"next line";

						int exampleValue = 1 + 2 +
												3 + 4 + (int)
						'2';

						if (exampleString.charAt(
						0
						)
												>
						exampleValue) {
												exampleValue =
												'b' - 'a';
						}

				//
				//
		/*
  comment
		 */
						}

						String commentString = "/*";
			String notIgnored = "1"; // causes result to be 3 instead of 6

						/*
		\/* tricks with backslash don't even cause any problems, \ can not be found outside of a comment or a string
		 */
						/*
		*\\/ */	/*	 i don't even know what this line is about	/** *\ */
		 /**/

						/**
		* "string inside the comment with *//* breaks the comment, so we need a new start""
		* so these lines don't cause any problems, 'cause the are ignored
						 */

/*
/**//* this triggers comment and and /*/ /* so does this
 but */ String another = "/*/"; /* doesn't open a new one
but closes an opened one String another2 = "/*/

}
