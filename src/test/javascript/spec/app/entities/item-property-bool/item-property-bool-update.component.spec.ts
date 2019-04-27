/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyBoolUpdateComponent } from 'app/entities/item-property-bool/item-property-bool-update.component';
import { ItemPropertyBoolService } from 'app/entities/item-property-bool/item-property-bool.service';
import { ItemPropertyBool } from 'app/shared/model/item-property-bool.model';

describe('Component Tests', () => {
    describe('ItemPropertyBool Management Update Component', () => {
        let comp: ItemPropertyBoolUpdateComponent;
        let fixture: ComponentFixture<ItemPropertyBoolUpdateComponent>;
        let service: ItemPropertyBoolService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyBoolUpdateComponent]
            })
                .overrideTemplate(ItemPropertyBoolUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemPropertyBoolUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyBoolService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyBool(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyBool = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyBool();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyBool = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
