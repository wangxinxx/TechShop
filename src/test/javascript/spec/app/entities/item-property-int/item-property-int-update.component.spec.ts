/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyIntUpdateComponent } from 'app/entities/item-property-int/item-property-int-update.component';
import { ItemPropertyIntService } from 'app/entities/item-property-int/item-property-int.service';
import { ItemPropertyInt } from 'app/shared/model/item-property-int.model';

describe('Component Tests', () => {
    describe('ItemPropertyInt Management Update Component', () => {
        let comp: ItemPropertyIntUpdateComponent;
        let fixture: ComponentFixture<ItemPropertyIntUpdateComponent>;
        let service: ItemPropertyIntService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyIntUpdateComponent]
            })
                .overrideTemplate(ItemPropertyIntUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemPropertyIntUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyIntService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyInt(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyInt = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemPropertyInt();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemPropertyInt = entity;
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
