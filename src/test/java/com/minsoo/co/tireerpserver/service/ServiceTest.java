package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseContentService;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireMemoService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
public abstract class ServiceTest {

    protected static final Logger log = LoggerFactory.getLogger(ServiceTest.class);

    @Autowired
    protected EntityManager em;

    @Autowired
    protected BrandService brandService;

    @Autowired
    protected PatternService patternService;

    @Autowired
    protected VendorService vendorService;

    @Autowired
    protected WarehouseService warehouseService;

    @Autowired
    protected TireService tireService;

    @Autowired
    protected TireDotService tireDotService;

    @Autowired
    protected TireMemoService tireMemoService;

    @Autowired
    protected PurchaseService purchaseService;

    @Autowired
    protected PurchaseContentService purchaseContentService;

    @Autowired
    protected StockService stockService;

    protected void clear() {
        em.flush();
        em.clear();
    }
}
