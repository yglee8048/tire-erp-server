package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.management.service.BrandService;
import com.minsoo.co.tireerpserver.management.service.PatternService;
import com.minsoo.co.tireerpserver.management.service.VendorService;
import com.minsoo.co.tireerpserver.management.service.WarehouseService;
import com.minsoo.co.tireerpserver.purchase.service.PurchaseContentService;
import com.minsoo.co.tireerpserver.purchase.service.PurchaseService;
import com.minsoo.co.tireerpserver.stock.service.StockService;
import com.minsoo.co.tireerpserver.tire.service.TireDotService;
import com.minsoo.co.tireerpserver.tire.service.TireMemoService;
import com.minsoo.co.tireerpserver.tire.service.TireService;
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
