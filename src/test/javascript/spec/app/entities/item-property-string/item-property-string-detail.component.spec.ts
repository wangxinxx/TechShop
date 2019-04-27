/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyStringDetailComponent } from 'app/entities/item-property-string/item-property-string-detail.component';
import { ItemPropertyString } from 'app/shared/model/item-property-string.model';

describe('Component Tests', () => {
    describe('ItemPropertyString Management Detail Component', () => {
        let comp: ItemPropertyStringDetailComponent;
        let fixture: ComponentFixture<ItemPropertyStringDetailComponent>;
        const route = ({ data: of({ itemPropertyString: new ItemPropertyString(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyStringDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemPropertyStringDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyStringDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemPropertyString).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
