/*******************************************************************************
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 * 
 * This file is part of the Alfresco Mobile SDK.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.application.fragments.properties;

import org.alfresco.mobile.android.api.constants.ContentModel;
import org.alfresco.mobile.android.api.model.Node;
import org.alfresco.mobile.android.application.R;
import org.alfresco.mobile.android.application.manager.ActionManager;
import org.alfresco.mobile.android.application.utils.SessionUtils;
import org.alfresco.mobile.android.ui.properties.PropertiesFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MetadataFragment extends PropertiesFragment
{
    public static final String TAG = "MetadataFragment";

    public static MetadataFragment newInstance(Node n)
    {
        MetadataFragment bf = new MetadataFragment();
        bf.setArguments(createBundleArgs(n));
        return bf;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        alfSession = SessionUtils.getsession(getActivity());
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(false);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (container == null) { return null; }
        alfSession = SessionUtils.getsession(getActivity());
        node = (Node) getArguments().get(ARGUMENT_NODE);

        LinearLayout v = new LinearLayout(getActivity());
        v.setOrientation(LinearLayout.VERTICAL);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        v.setGravity(Gravity.CENTER);
        
        //View v = inflater.inflate(R.layout.sdk_property_title, container, false);

        // ASPECTS
        ViewGroup parent = (ViewGroup) v;
        
        createAspectPanel(inflater, parent, node, ContentModel.ASPECT_GENERAL, null, null, false);
        createAspectPanel(inflater, parent, node, ContentModel.ASPECT_GEOGRAPHIC, R.drawable.ic_location,
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ActionManager.actionShowMap(MetadataFragment.this, node.getName(),
                                node.getProperty(ContentModel.PROP_LATITUDE).getValue().toString(),
                                node.getProperty(ContentModel.PROP_LONGITUDE).getValue().toString());
                    }
                });
        createAspectPanel(inflater, parent, node, ContentModel.ASPECT_EXIF, null, null);
        createAspectPanel(inflater, parent, node, ContentModel.ASPECT_AUDIO, null, null);

        return v;
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }
}
