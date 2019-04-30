/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TechShopTestModule } from '../../../test.module';
import { PassportDeleteDialogComponent } from 'app/entities/passport/passport-delete-dialog.component';
import { PassportService } from 'app/entities/passport/passport.service';

describe('Component Tests', () => {
    describe('Passport Management Delete Component', () => {
        let comp: PassportDeleteDialogComponent;
        let fixture: ComponentFixture<PassportDeleteDialogComponent>;
        let service: PassportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [PassportDeleteDialogComponent]
            })
                .overrideTemplate(PassportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PassportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassportService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
