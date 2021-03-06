/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.ui.freemarker.tag;

import java.math.BigInteger;

import com.jfinal.plugin.activerecord.Page;

import io.jpress.core.render.freemarker.JTag;
import io.jpress.model.Content;
import io.jpress.model.query.ContentQuery;

public class IndexPageTag extends JTag {

	int pageNumber;
	String pagePara;

	public IndexPageTag(String pagePara,int pageNumber) {
		this.pagePara = pagePara;
		this.pageNumber = pageNumber;
	}

	@Override
	public void onRender() {

		String orderBy = getParam("orderby");
		String keyword = getParam("keyword");

		int pagesize = getParamToInt("pagesize", 10);

		BigInteger[] typeIds = getParamToBigIntegerArray("typeid");
		String module = getParam("module");
		String status = getParam("status", Content.STATUS_NORMAL);

		Page<Content> cpage = ContentQuery.me().paginate(pageNumber, pagesize, module, keyword, status, typeIds, null,
				orderBy);
		setVariable("page", cpage);
		

		IndexPaginateTag indexPagination = new IndexPaginateTag(cpage,pagePara);
		setVariable("pagination", indexPagination);

		renderBody();
	}

}
