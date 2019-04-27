/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyBoolDeleteDialogComponent } from 'app/entities/item-property-bool/item-property-bool-delete-dialog.component';
import { ItemPropertyBoolService } from 'app/entities/item-property-bool/item-property-bool.service';

describe('Component Tests', () => {
    describe('ItemPropertyBool Management Delete Component', () => {
        let comp: ItemPropertyBoolDeleteDialogComponent;
        let fixture: ComponentFixture<ItemPropertyBoolDeleteDialogComponent>;
        let service: ItemPropertyBoolService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyBoolDeleteDialogComponent]
            })
                .overrideTemplate(ItemPropertyBoolDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyBoolDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyBoolService);
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
