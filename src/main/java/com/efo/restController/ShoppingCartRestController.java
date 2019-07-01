package com.efo.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.entity.MarketPlaceProducts;
import com.efo.entity.Products;
import com.efo.entity.ShoppingCart;
import com.efo.entity.ShoppingCartItems;
import com.efo.service.MarketPlaceProductsService;
import com.efo.service.ProductsService;
import com.efo.service.ShoppingCartItemsService;
import com.efo.service.ShoppingCartService;

@RestController
@RequestMapping("/rest/")
public class ShoppingCartRestController {

	@Value("${efo.payment.gateway}")
	private String gateway;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ShoppingCartItemsService shoppingCartItemsService;

	@Autowired
	private MarketPlaceProductsService marketPlaceProductsService;

	@Autowired
	private ProductsService productsService;

	@RequestMapping("addshoppingcartitem")
	public String addShoppingCartItem(@RequestParam(value = "cartID") String cartId, @RequestParam(value = "prdId") String prdId,
			@RequestParam(value = "qty") int qty) throws JSONException {
		ShoppingCartItems item = null;

		ShoppingCart cart = shoppingCartService.retrieve(cartId);
		
		
		if (shoppingCartItemsService.checkIfItemExists(cartId, prdId)) {
			return resultToJSON("ERROR", "You already have this in your shopping cart");
		} else {
			item = new ShoppingCartItems();
			item.setShoppingCart(cart);
			cart.getShoppingCartItems().add(item);

			if (prdId.startsWith("EFO")) {
				Products product = productsService.retrieve(prdId);
				item.setReference(cartId);
				item.setProduct_name(product.getProduct_name());
				item.setProduct_id(prdId);
				item.setFile_name(product.getFile_name());
				item.setQty(qty);
				item.setProduct_price(product.getProduct_price());
				item.setProduct_discount(product.getProduct_discount());
				if (product.getProduct_tax() == null)
					product.setProduct_tax(0.0);
				item.setProduct_tax(product.getProduct_tax());
			} else {
				MarketPlaceProducts product = marketPlaceProductsService.retrieve(Long.valueOf(prdId));
				item.setReference(cartId);
				item.setProduct_name(product.getProduct_name());
				item.setProduct_id(prdId);
				item.setFile_name(product.getFile_name());
				item.setQty(qty);
				item.setProduct_price(product.getProduct_price());
				item.setProduct_discount(0.0);
				if (product.getProduct_tax() == null)
					product.setProduct_tax(0.0);
				item.setProduct_tax(product.getProduct_tax());
			}
		}
		if (shoppingCartService.checkHistory(prdId, cart.getUser_id()) ) {
			item.setProduct_price(0.0);
			item.setProduct_discount(0.0);
			item.setProduct_tax(0.0);
		}
		if (item.getQty() > 1) {
			return resultToJSON("ERROR", "You are only allowed 1 of this item");
		} else {
			shoppingCartService.merge(cart);
			return resultToJSON("SUCCESS", "Item Successfully added");
		}
	}

	@RequestMapping("deleteshoppingcartitem")
	public String deleteShoppingCartItem(@RequestParam(value = "cartID") String cartId, @RequestParam(value = "prdId") String prdId) throws JSONException {

		shoppingCartItemsService.deleteShoppingCartItem(cartId, prdId);

		return resultToJSON("SUCCESS", "Item Successfully removed");
	}

	@RequestMapping("shoppingcartcount")
	public String shoppingCartCount(@RequestParam(value = "username") String username) throws JSONException {
		JSONObject json = new JSONObject();

		Long count = shoppingCartService.getShoppingCartCount(username);

		if (count == null)
			count = 0L;

		json.put("count", count);

		return json.toString();

	}

	private String resultToJSON(String result, String msg) throws JSONException {
		JSONObject json = new JSONObject();

		json.put("result", result);
		json.put("msg", msg);

		return json.toString();
	}

}
