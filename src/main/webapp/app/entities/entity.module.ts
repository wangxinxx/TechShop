import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'property',
                loadChildren: './property/property.module#TechShopPropertyModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#TechShopProductModule'
            },
            {
                path: 'property',
                loadChildren: './property/property.module#TechShopPropertyModule'
            },
            {
                path: 'item',
                loadChildren: './item/item.module#TechShopItemModule'
            },
            {
                path: 'item-property-string',
                loadChildren: './item-property-string/item-property-string.module#TechShopItemPropertyStringModule'
            },
            {
                path: 'item',
                loadChildren: './item/item.module#TechShopItemModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopEntityModule {}
