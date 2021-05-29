package com.analysis.wisdomtraffic.adapter;

import com.analysis.wisdomtraffic.been.ItemData;
import com.analysis.wisdomtraffic.been.RootData;
import com.analysis.wisdomtraffic.ui.filemanage.provider.RootNodeProvider;
import com.analysis.wisdomtraffic.ui.filemanage.provider.SecondNodeProvider;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 15:05 2021/3/26 0026
 * @Description com.analysis.wisdomtraffic.adapter
 **/
public class PicNodeAdapter extends BaseNodeAdapter {

    public PicNodeAdapter() {
        super();
    }

    public void setNodeProvider(SecondNodeProvider secondNodeProvider){
        addFullSpanNodeProvider(new RootNodeProvider());
        addNodeProvider(secondNodeProvider);
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof RootData){
            return 0;
        }else if(node instanceof ItemData){
            return 1;
        }
        return -1;
    }
}
