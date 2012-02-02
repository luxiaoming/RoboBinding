/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.ratingbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.IntegerRatingAttribute;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class IntegerRatingAttributeTest extends AbstractPropertyViewAttributeTest<RatingBar, IntegerRatingAttribute>
{
	@Before
	public void setUp()
	{
		//TODO Remove this line once Robolectric auto-initializes this field to 100 as it should do (pull request submitted)
		view.setMax(100);
	}
	
	@Test
	public void whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		int newRating = RandomValues.anyInteger();
		
		attribute.valueModelUpdated(newRating);

		assertThat(view.getRating(), equalTo((float)newRating));
	}
	
	@Test
	@Ignore //TODO Robolectric error (pull request submitted)
	public void whenRatingIsChanged_thenUpdateValueModel()
	{
		ValueModel<Integer> valueModel = twoWayBindToProperty(Integer.class);
		
		view.setRating(RandomValues.anyInteger());
		
		assertThat((float)valueModel.getValue(), equalTo(view.getRating()));
	}
}