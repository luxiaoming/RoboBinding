/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.binder;

import java.util.Map;
import java.util.Queue;

import org.robobinding.customview.BindableView;
import org.robobinding.customview.CustomViewUtils;
import org.robobinding.viewattribute.BindingAttributeProvider;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class BindingAttributeProvidersResolver {
    private final Map<Class<?>, BindingAttributeProvider<? extends View>> bindingAttributeProvidersMap;

    public BindingAttributeProvidersResolver(Map<Class<?>, BindingAttributeProvider<? extends View>> bindingAttributeProvidersMap) {
	this.bindingAttributeProvidersMap = bindingAttributeProvidersMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Iterable<BindingAttributeProvider<? extends View>> getCandidateProviders(View view) {
	Queue<BindingAttributeProvider<? extends View>> candidateProviders = Lists.newLinkedList();

	if (CustomViewUtils.isCustomWidget(view)) {
	    candidateProviders.add(CustomViewUtils.adapt((BindableView) view));
	}

	processViewHierarchy(view.getClass(), candidateProviders);
	return candidateProviders;
    }

    private void processViewHierarchy(Class<?> clazz, Queue<BindingAttributeProvider<? extends View>> candidateProviders) {
	BindingAttributeProvider<? extends View> bindingAttributeProvider = lookupProviderForViewType(clazz);

	if (bindingAttributeProvider != null)
	    candidateProviders.add(bindingAttributeProvider);

	if (clazz != View.class)
	    processViewHierarchy(clazz.getSuperclass(), candidateProviders);
    }

    private BindingAttributeProvider<? extends View> lookupProviderForViewType(Class<?> clazz) {
	return bindingAttributeProvidersMap.get(clazz);
    }
}