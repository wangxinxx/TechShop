/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyBoolDetailComponent } from 'app/entities/item-property-bool/item-property-bool-detail.component';
import { ItemPropertyBool } from 'app/shared/model/item-property-bool.model';

describe('Component Tests', () => {
    describe('ItemPropertyBool Management Detail Component', () => {
        let comp: ItemPropertyBoolDetailComponent;
        let fixture: ComponentFixture<ItemPropertyBoolDetailComponent>;
        const route = ({ data: of({ itemPropertyBool: new ItemPropertyBool(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyBoolDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemPropertyBoolDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyBoolDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemPropertyBool).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
