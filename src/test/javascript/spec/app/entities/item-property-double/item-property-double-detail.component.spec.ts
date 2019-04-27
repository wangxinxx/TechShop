/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyDoubleDetailComponent } from 'app/entities/item-property-double/item-property-double-detail.component';
import { ItemPropertyDouble } from 'app/shared/model/item-property-double.model';

describe('Component Tests', () => {
    describe('ItemPropertyDouble Management Detail Component', () => {
        let comp: ItemPropertyDoubleDetailComponent;
        let fixture: ComponentFixture<ItemPropertyDoubleDetailComponent>;
        const route = ({ data: of({ itemPropertyDouble: new ItemPropertyDouble(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyDoubleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemPropertyDoubleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyDoubleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemPropertyDouble).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
