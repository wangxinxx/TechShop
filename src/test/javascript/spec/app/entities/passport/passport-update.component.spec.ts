/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { PassportUpdateComponent } from 'app/entities/passport/passport-update.component';
import { PassportService } from 'app/entities/passport/passport.service';
import { Passport } from 'app/shared/model/passport.model';

describe('Component Tests', () => {
    describe('Passport Management Update Component', () => {
        let comp: PassportUpdateComponent;
        let fixture: ComponentFixture<PassportUpdateComponent>;
        let service: PassportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [PassportUpdateComponent]
            })
                .overrideTemplate(PassportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PassportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassportService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Passport(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.passport = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Passport();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.passport = entity;
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
