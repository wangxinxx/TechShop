import { NgModule } from '@angular/core';

import { TechShopSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TechShopSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TechShopSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TechShopSharedCommonModule {}
