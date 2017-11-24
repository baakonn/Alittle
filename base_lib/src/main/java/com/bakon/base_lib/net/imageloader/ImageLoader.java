/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bakon.base_lib.net.imageloader;

import android.content.Context;
import android.text.TextUtils;

import com.bakon.base_lib.R;
import com.bumptech.glide.Glide;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * ================================================
 * {@link ImageLoader} Glide
 * ================================================
 */
@Singleton
public class ImageLoader {

    @Inject
    public ImageLoader() {
    }

    public void loadImage(Context ctx, ImageConfigImpl config) {
        Glide.with(ctx)
                .load(config.getUrl())
                .error(config.errorPic == 0 ? R.drawable.default_img : config.errorPic)
                .placeholder(config.placeholder == 0 ? R.drawable.default_img : config.placeholder)
                .into(config.getImageView());

    }
}
