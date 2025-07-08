package guidemon.model.item.interfaces;

import java.util.List;

import guidemon.model.stats.immutable.instances.ResourceEntry;

public interface Resourceable {
    List<ResourceEntry> getItemCharges();

    List<ResourceEntry> getStoredResources(); 
}