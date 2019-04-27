/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyIntDetailComponent } from 'app/entities/item-property-int/item-property-int-detail.component';
import { ItemPropertyInt } from 'app/shared/model/item-property-int.model';

describe('Component Tests', () => {
    describe('ItemPropertyInt Management Detail Component', () => {
        let comp: ItemPropertyIntDetailComponent;
        let fixture: ComponentFixture<ItemPropertyIntDetailComponent>;
        const route = ({ data: of({ itemPropertyInt: new ItemPropertyInt(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyIntDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemPropertyIntDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyIntDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemPropertyInt).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
