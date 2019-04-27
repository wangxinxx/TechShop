/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyFloatDetailComponent } from 'app/entities/item-property-float/item-property-float-detail.component';
import { ItemPropertyFloat } from 'app/shared/model/item-property-float.model';

describe('Component Tests', () => {
    describe('ItemPropertyFloat Management Detail Component', () => {
        let comp: ItemPropertyFloatDetailComponent;
        let fixture: ComponentFixture<ItemPropertyFloatDetailComponent>;
        const route = ({ data: of({ itemPropertyFloat: new ItemPropertyFloat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyFloatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemPropertyFloatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyFloatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemPropertyFloat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
