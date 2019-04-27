/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyStringUpdateComponent } from 'app/entities/item-property-string/item-property-string-update.component';
import { ItemPropertyStringService } from 'app/entities/item-property-string/item-property-string.service';
import { ItemPropertyString } from 'app/shared/model/item-property-string.model';

describe('Component Tests', () => {
    describe('ItemPropertyString Management Update Component', () => {
        let comp: ItemPropertyStringUpdateComponent;
        let fixture: ComponentFixture<ItemPropertyStringUpdateComponent>;
        let service: ItemPropertyStringService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyStringUpdateComponent]
            })
                .overrideTemplate(ItemPropertyStringUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemPropertyStringUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyStringService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyString(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyString = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyString();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyString = entity;
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
