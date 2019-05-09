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
            },
            {
                path: 'item-property-int',
                loadChildren: './item-property-int/item-property-int.module#TechShopItemPropertyIntModule'
            },
            {
                path: 'item-property-float',
                loadChildren: './item-property-float/item-property-float.module#TechShopItemPropertyFloatModule'
            },
            {
                path: 'item-property-bool',
                loadChildren: './item-property-bool/item-property-bool.module#TechShopItemPropertyBoolModule'
            },
            {
                path: 'item-property-double',
                loadChildren: './item-property-double/item-property-double.module#TechShopItemPropertyDoubleModule'
            },
            {
                path: 'item-property-double',
                loadChildren: './item-property-double/item-property-double.module#TechShopItemPropertyDoubleModule'
            },
            {
                path: 'item-property-double',
                loadChildren: './item-property-double/item-property-double.module#TechShopItemPropertyDoubleModule'
            },
            {
                path: 'passport',
                loadChildren: './passport/passport.module#TechShopPassportModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'permission',
                loadChildren: './permission/permission.module#TechShopPermissionModule'
            },
            {
                path: 'position',
                loadChildren: './position/position.module#TechShopPositionModule'
            },
            {
                path: 'permission',
                loadChildren: './permission/permission.module#TechShopPermissionModule'
            },
            {
                path: 'position',
                loadChildren: './position/position.module#TechShopPositionModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'passport',
                loadChildren: './passport/passport.module#TechShopPassportModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification.module#TechShopNotificationModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification.module#TechShopNotificationModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
            },
            {
                path: 'country',
                loadChildren: './country/country.module#TechShopCountryModule'
            },
            {
                path: 'city',
                loadChildren: './city/city.module#TechShopCityModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#TechShopProfileModule'
            },
            {
                path: 'item',
                loadChildren: './item/item.module#TechShopItemModule'
            },
            {
                path: 'item',
                loadChildren: './item/item.module#TechShopItemModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#TechShopProductModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
            },
            {
                path: 'operation',
                loadChildren: './operation/operation.module#TechShopOperationModule'
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
