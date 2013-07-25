package devopsdistilled.operp.server.data.service.items;

import devopsdistilled.operp.server.data.entity.items.Item;
import devopsdistilled.operp.server.data.service.EntityService;

public interface ItemService extends EntityService<Item, Long> {

	boolean isProductBrandPairExists(String productName, String brandName);

	boolean isItemNameExists(String itemName);

}
